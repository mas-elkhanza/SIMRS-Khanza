

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputSttswp&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListSttswp>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $stts     =str_replace("_"," ",isset($_GET['stts']))?str_replace("_"," ",$_GET['stts']):NULL;
                if($action == "TAMBAH"){
                    $stts      = str_replace("_"," ",isset($_GET['stts']))?str_replace("_"," ",$_GET['stts']):NULL;
                    $ktg      = "";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT * FROM stts_wp WHERE stts='$stts'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $stts         = $baris[0];
                    $ktg          = $baris[1];
                }
                echo"<input type=hidden name=stts value=$stts><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Status WP</td><td width="">:</td>
                    <td width="67%"><input name="stts" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $stts;?>" size="10" maxlength="5">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="ktg" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $ktg;?>" size="50" maxlength="50" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $stts    = validTeks(trim($_POST['stts']));
                    $ktg    = validTeks(trim($_POST['ktg']));
                    if ((!empty($stts))&&(!empty($ktg))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" stts_wp "," '$stts','$ktg' ", " status WP " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputSttswp&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" stts_wp "," ktg='$ktg' WHERE stts='$stts' ", " status WP ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListSttswp'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($stts))||(empty($ktg))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>

