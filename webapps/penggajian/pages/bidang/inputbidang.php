

<div id="post">
   <div align="center" class="link">
        <a href=?act=InputBidang&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListBidang>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_ruang" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $nama        =isset($_GET['nama'])?$_GET['nama']:NULL;
                if($action == "TAMBAH"){
                    $nama      = isset($_GET['nama'])?$_GET['nama']:NULL;
                }else if($action == "UBAH"){
                    $_sql         = "SELECT nama FROM bidang WHERE nama='$nama'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $nama      = $baris[0];
                }
                echo"<input type=hidden name=nama value=$nama><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Bidang</td><td width="">:</td>
                    <td width="67%"><input name="nama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $nama;?>" size="30" maxlength="15">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $nama   = trim(isset($_POST['nama']))?trim($_POST['nama']):NULL;
                    if (!empty($nama)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" bidang "," '$nama' ", " Bidang " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputBidang&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" bidang "," nama='$nama' WHERE nama='".$_GET['nama']."' ", "bidang");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListBidang'></head><body></body></html>";
                                break;
                        }
                    }else if (empty($nama)){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>
