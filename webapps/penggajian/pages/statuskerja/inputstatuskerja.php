

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputSttskerja&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListSttskerja>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action   =isset($_GET['action'])?$_GET['action']:NULL;
                $stts     =str_replace("_"," ",isset($_GET['stts'])?$_GET['stts']:NULL);
                if($action == "TAMBAH"){
                    $stts       = str_replace("_"," ",isset($_GET['stts']))?str_replace("_"," ",$_GET['stts']):NULL;
                    $ktg        = "";
                    $indek      ="";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT * FROM stts_kerja WHERE stts='$stts'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $stts         = $baris[0];
                    $ktg          = $baris[1];
                    $indek        = $baris[2];
                }
                echo"<input type=hidden name=stts value=$stts><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Status</td><td width="">:</td>
                    <td width="67%"><input name="stts" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $stts;?>" size="10" maxlength="3">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="ktg" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $ktg;?>" size="40" maxlength="20" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Index Status</td><td width="">:</td>
                    <td width="67%"><input name="indek" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $indek;?>" size="10" maxlength="2" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $stts    = validTeks(trim($_POST['stts']));
                    $ktg    = validTeks(trim($_POST['ktg']));
                    $indek   = validTeks(trim($_POST['indek']));
                    if ((!empty($stts))&&(!empty($ktg))&&(!empty($indek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" stts_kerja "," '$stts','$ktg','$indek' ", " status kerja " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputSttskerja&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" stts_kerja "," ktg='$ktg',indek='$indek' WHERE stts='$stts' ", " status kerja ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListSttskerja'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($stts))||(empty($ktg))||(empty($indek))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>

