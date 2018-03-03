

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputBank&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListBank>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:null;
                $namabank     =str_replace("_"," ",isset($_GET['namabank']))?str_replace("_"," ",$_GET['namabank']):NULL;
                if($action == "TAMBAH"){
                    $namabank      = str_replace("_"," ",isset($_GET['namabank']))?str_replace("_"," ",$_GET['namabank']):NULL;
                }else if($action == "UBAH"){
                    $_sql         = "SELECT * FROM bank WHERE namabank='$namabank'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $namabank         = $baris[0];
                }
                echo"<input type=hidden name=namabank value=$namabank><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Nama Bank</td><td width="">:</td>
                    <td width="67%"><input name="namabank" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $namabank;?>" size="40" maxlength="50">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $namabank    = trim($_POST['namabank']);
                    if (!empty($namabank)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" bank "," '$namabank'", " Bank " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputBank&action=TAMBAH'></head><body></body></html>";
                                break;
                        }
                    }else if (empty($namabank)){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>
