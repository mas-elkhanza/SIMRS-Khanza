

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputFungsional&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListFungsional>| List Data |</a>
    </div>
    <div class="entry">
        <form name="frm_fungsional" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =$_GET['action'];
                $TxtKode     =$_GET['kode'];
                if($action == "TAMBAH"){
                    $TxtKode       = $_GET['TxtKode'];;
                    $TxtNama     = "";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT * FROM fungsional WHERE kode='$TxtKode'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $TxtKode      = $baris[0];
                    $TxtNama    = $baris[1];
                }
                echo"<input type=hidden name=TxtKode value=$TxtKode><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Kode Jabatan</td><td width="">:</td>
                    <td width="67%"><input name="TxtKode" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $TxtKode;?>" size="20" maxlength="2">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jabatan Fungsional</td><td width="">:</td>
                    <td width="67%"><input name="TxtNama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $TxtNama;?>" size="55" maxlength="50" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=$_POST['BtnSimpan'];
                if (isset($BtnSimpan)) {
                    $TxtKode   = trim($_POST['TxtKode']);
                    $TxtNama = trim($_POST['TxtNama']);
                    if ((!empty($TxtKode))&&(!empty($TxtNama))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" fungsional "," '$TxtKode','$TxtNama' ", " Jabatan Fungsional " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputFungsional&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" fungsional "," nama='$TxtNama' WHERE kode='".$_GET['kode']."' ", "jabatan fungsional");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListFungsional'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($TxtKode))||(empty($TxtNama))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>
