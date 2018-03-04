

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputDepartemen&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListDepartemen>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:null;
                $dep_id     =str_replace("_"," ",isset($_GET['dep_id']))?str_replace("_"," ",$_GET['dep_id']):NULL;
                if($action == "TAMBAH"){
                    $dep_id      = str_replace("_"," ",isset($_GET['dep_id']))?str_replace("_"," ",$_GET['dep_id']):NULL;
                    $nama      = "";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT * FROM departemen WHERE dep_id='$dep_id'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $dep_id         = $baris[0];
                    $nama          = $baris[1];
                }
                echo"<input type=hidden name=dep_id value=$dep_id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Dep ID</td><td width="">:</td>
                    <td width="67%"><input name="dep_id" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $dep_id;?>" size="10" maxlength="4">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Nama Departemen</td><td width="">:</td>
                    <td width="67%"><input name="nama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $nama;?>" size="40" maxlength="25" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $dep_id    = trim($_POST['dep_id']);
                    $nama    = trim($_POST['nama']);
                    if ((!empty($dep_id))&&(!empty($nama))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" departemen "," '$dep_id','$nama' ", " Departemen " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputDepartemen&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" departemen "," nama='$nama' WHERE dep_id='$dep_id' ", " Departemen ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListDepartemen'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($dep_id))||(empty($nama))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>
